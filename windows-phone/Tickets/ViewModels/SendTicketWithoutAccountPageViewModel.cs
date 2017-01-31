using Caliburn.Micro;
using Tickets.Models;

namespace Tickets.ViewModels
{
    public class SendTicketWithoutAccountPageViewModel : PropertyChangedBase
    {
        private readonly INavigationService _navigationService;

        public User User { get; set; }

        public SendTicketWithoutAccountPageViewModel(INavigationService navigationService)
        {
            _navigationService = navigationService;
        }

        public void SendTicket()
        {
            _navigationService.For<MyTicketsPageViewModel>().Navigate();
        }
    }
}
